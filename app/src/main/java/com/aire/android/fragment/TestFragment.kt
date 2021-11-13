package com.aire.android.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aire.android.test.R

/**
 * @author ZhuPeipei
 * @date 2021/11/11 17:41
 */
class TestFragment : Fragment() {
    companion object {
        const val TAG = "TestFragment"
    }

    private var mRootView: View? = null
    private var mViewPager: ViewPager? = null
    private var mAdapter: FragmentStatePagerAdapter? = null
    private val mList = ArrayList<String>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: ")
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        Log.i(TAG, "onAttachFragment: " + childFragment)
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG, "setUserVisibleHint: " + isVisibleToUser)
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: " + container)
        val view = inflater.inflate(R.layout.main_fragment_test, container, false)
        mRootView = view
        initUi()
        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initUi() {
        mViewPager = mRootView?.findViewById(R.id.main_viewpager_1)
        mList.add("test1")
        mList.add("test2")
        mList.add("test3")
        mList.add("test4")

        mAdapter = object :
            FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount() = mList.size

            override fun getItem(position: Int): Fragment {
                Log.i(TAG, "getItem: $position")
                return ChildFragment("${mList[position]}-$position")
            }

            override fun getItemPosition(`object`: Any): Int {
                return super.getItemPosition(`object`)
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                super.destroyItem(container, position, `object`)
                Log.i(TAG, "destroyItem1: $position")
            }

            override fun destroyItem(container: View, position: Int, `object`: Any) {
                super.destroyItem(container, position, `object`)
                Log.i(TAG, "destroyItem2: $position")
            }
        }
        mViewPager?.adapter = mAdapter
//        mViewPager?.offscreenPageLimit = 10
        mAdapter?.notifyDataSetChanged()
//        mViewPager?.currentItem = 3

//        mRootView?.postDelayed(Runnable {
//            mList.add(1, "test5")
//            mAdapter?.notifyDataSetChanged()
//        }, 6000)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
//        Log.i(TAG, "onStart: ${Log.getStackTraceString(Throwable())}")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume:")
//        Log.i(TAG, "onResume: ${Log.getStackTraceString(Throwable())}")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }
}

class ChildFragment(private val name: String) : Fragment() {
    val TAG = "childFrag $name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return super.onCreateAnimation(transit, enter, nextAnim)
        Log.i(TAG, "onCreateAnimation: ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.i(TAG, "setUserVisibleHint: $isVisibleToUser")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i(TAG, "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: ")
        val tv = TextView(context)
        tv.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        tv.gravity = Gravity.CENTER
        tv.textSize = 30 * 1.5f
        tv.text = name
        return tv
    }
}